package com.meatshopmanager.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.LucroRealDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;
import com.meatshopmanager.model.Expense;
import com.meatshopmanager.model.Receita;
import com.meatshopmanager.repository.ExpenseRepository;
import com.meatshopmanager.repository.ReceitaRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseRepository repository;
    private final ReceitaRepository receitaRepository;

    public DashboardServiceImpl(ExpenseRepository repository, ReceitaRepository receitaRepository) {
        this.repository = repository;
        this.receitaRepository = receitaRepository;
    }

    @Override
    public TotalExpenseDTO getTotalExpenses() {
        BigDecimal total = repository.getTotalExpenses();
        return new TotalExpenseDTO(total == null ? BigDecimal.ZERO : total);
    }

    @Override
    public List<ExpenseByCategoryDTO> getTotalByCategory() {
        return repository.getTotalByCategory();
    }

    @Override
    public List<ExpenseByMonthDTO> getTotalByMonth() {
        return repository.getTotalByMonth();
    }

    @Override
    public LucroRealDTO getLucroReal() {
        BigDecimal totalDespesas = repository.getTotalExpenses();
        totalDespesas = totalDespesas == null ? BigDecimal.ZERO : totalDespesas;

        BigDecimal totalReceitas = receitaRepository.getTotalReceitas();
        totalReceitas = totalReceitas == null ? BigDecimal.ZERO : totalReceitas;

        BigDecimal lucro = totalReceitas.subtract(totalDespesas);

        return new LucroRealDTO(totalReceitas, totalDespesas, lucro);
    }

    @Override
    public byte[] gerarRelatorioExcel(LocalDate dataInicio, LocalDate dataFim) {
        List<Expense> despesas = repository
                .findComFiltros(null, dataInicio, dataFim, Pageable.unpaged())
                .getContent();
        List<Receita> receitas = receitaRepository
                .findComFiltros(null, dataInicio, dataFim, Pageable.unpaged())
                .getContent();

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            CellStyle headerStyle = workbook.createCellStyle();
            var headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            BigDecimal totalDespesas = criarAbaDespesas(workbook, headerStyle, despesas);
            BigDecimal totalReceitas = criarAbaReceitas(workbook, headerStyle, receitas);
            criarAbaResumo(workbook, headerStyle, totalDespesas, totalReceitas);

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório Excel", e);
        }
    }

    private BigDecimal criarAbaDespesas(XSSFWorkbook workbook, CellStyle headerStyle, List<Expense> despesas) {
        Sheet sheet = workbook.createSheet("Despesas");
        criarCabecalho(sheet, headerStyle, new String[]{"Descrição", "Categoria", "Valor", "Data"});
        BigDecimal total = BigDecimal.ZERO;
        int rowIdx = 1;
        for (Expense e : despesas) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.getDescription());
            row.createCell(1).setCellValue(e.getCategoria().getNome());
            row.createCell(2).setCellValue(e.getAmount().doubleValue());
            row.createCell(3).setCellValue(e.getExpenseDate().toString());
            total = total.add(e.getAmount());
        }
        return total;
    }

    private BigDecimal criarAbaReceitas(XSSFWorkbook workbook, CellStyle headerStyle, List<Receita> receitas) {
        Sheet sheet = workbook.createSheet("Receitas");
        criarCabecalho(sheet, headerStyle, new String[]{"Descrição", "Categoria", "Valor", "Data"});
        BigDecimal total = BigDecimal.ZERO;
        int rowIdx = 1;
        for (Receita r : receitas) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(r.getDescricao());
            row.createCell(1).setCellValue(r.getCategoria().getNome());
            row.createCell(2).setCellValue(r.getValor().doubleValue());
            row.createCell(3).setCellValue(r.getData().toString());
            total = total.add(r.getValor());
        }
        return total;
    }

    private void criarAbaResumo(XSSFWorkbook workbook, CellStyle headerStyle,
                                BigDecimal totalDespesas, BigDecimal totalReceitas) {
        Sheet sheet = workbook.createSheet("Resumo");
        criarCabecalho(sheet, headerStyle, new String[]{"Item", "Valor (R$)"});
        BigDecimal lucro = totalReceitas.subtract(totalDespesas);

        Row r1 = sheet.createRow(1);
        r1.createCell(0).setCellValue("Total de Despesas");
        r1.createCell(1).setCellValue(totalDespesas.doubleValue());

        Row r2 = sheet.createRow(2);
        r2.createCell(0).setCellValue("Total de Receitas");
        r2.createCell(1).setCellValue(totalReceitas.doubleValue());

        Row r3 = sheet.createRow(3);
        r3.createCell(0).setCellValue("Lucro Real");
        r3.createCell(1).setCellValue(lucro.doubleValue());
    }

    private void criarCabecalho(Sheet sheet, CellStyle headerStyle, String[] colunas) {
        Row header = sheet.createRow(0);
        for (int i = 0; i < colunas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(colunas[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    @Override
    public byte[] gerarRelatorioPdf(LocalDate dataInicio, LocalDate dataFim) {
        List<Expense> despesas = repository
                .findComFiltros(null, dataInicio, dataFim, Pageable.unpaged())
                .getContent();
        List<Receita> receitas = receitaRepository
                .findComFiltros(null, dataInicio, dataFim, Pageable.unpaged())
                .getContent();

        BigDecimal totalDespesas = despesas.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalReceitas = receitas.stream()
                .map(Receita::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lucro = totalReceitas.subtract(totalDespesas);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            Paragraph titulo = new Paragraph("Relatório Financeiro - MeatShop Manager", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            String periodoTexto = "Período: "
                    + (dataInicio != null ? dataInicio.toString() : "início")
                    + " a "
                    + (dataFim != null ? dataFim.toString() : "atual");
            Paragraph periodo = new Paragraph(periodoTexto, normalFont);
            periodo.setAlignment(Element.ALIGN_CENTER);
            document.add(periodo);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Resumo", boldFont));
            PdfPTable resumo = new PdfPTable(2);
            resumo.setWidthPercentage(60);
            adicionarLinhaResumo(resumo, "Total de Despesas", "R$ " + totalDespesas.toPlainString(), boldFont, normalFont);
            adicionarLinhaResumo(resumo, "Total de Receitas", "R$ " + totalReceitas.toPlainString(), boldFont, normalFont);
            adicionarLinhaResumo(resumo, "Lucro Real", "R$ " + lucro.toPlainString(), boldFont, normalFont);
            document.add(resumo);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Despesas", boldFont));
            document.add(criarTabelaDespesasPdf(despesas, boldFont, normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Receitas", boldFont));
            document.add(criarTabelaReceitasPdf(receitas, boldFont, normalFont));

        } catch (DocumentException e) {
            throw new RuntimeException("Erro ao gerar relatório PDF", e);
        } finally {
            document.close();
        }

        return out.toByteArray();
    }

    private void adicionarLinhaResumo(PdfPTable table, String label, String valor,
                                      Font boldFont, Font normalFont) {
        table.addCell(new PdfPCell(new Phrase(label, boldFont)));
        table.addCell(new PdfPCell(new Phrase(valor, normalFont)));
    }

    private PdfPTable criarTabelaDespesasPdf(List<Expense> despesas, Font boldFont, Font normalFont)
            throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        Color cinza = new Color(200, 200, 200);
        for (String col : new String[]{"Descrição", "Categoria", "Valor", "Data"}) {
            PdfPCell cell = new PdfPCell(new Phrase(col, boldFont));
            cell.setBackgroundColor(cinza);
            table.addCell(cell);
        }
        for (Expense e : despesas) {
            table.addCell(new Phrase(e.getDescription(), normalFont));
            table.addCell(new Phrase(e.getCategoria().getNome(), normalFont));
            table.addCell(new Phrase("R$ " + e.getAmount().toPlainString(), normalFont));
            table.addCell(new Phrase(e.getExpenseDate().toString(), normalFont));
        }
        return table;
    }

    private PdfPTable criarTabelaReceitasPdf(List<Receita> receitas, Font boldFont, Font normalFont)
            throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        Color cinza = new Color(200, 200, 200);
        for (String col : new String[]{"Descrição", "Categoria", "Valor", "Data"}) {
            PdfPCell cell = new PdfPCell(new Phrase(col, boldFont));
            cell.setBackgroundColor(cinza);
            table.addCell(cell);
        }
        for (Receita r : receitas) {
            table.addCell(new Phrase(r.getDescricao(), normalFont));
            table.addCell(new Phrase(r.getCategoria().getNome(), normalFont));
            table.addCell(new Phrase("R$ " + r.getValor().toPlainString(), normalFont));
            table.addCell(new Phrase(r.getData().toString(), normalFont));
        }
        return table;
    }
}
