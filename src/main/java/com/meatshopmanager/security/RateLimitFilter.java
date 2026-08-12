package com.meatshopmanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int LIMITE_REQUISICOES = 5;
    private static final long JANELA_MS = 60_000;

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (!request.getRequestURI().equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, k -> new Bucket());

        if (!bucket.permitir()) {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Too Many Requests\",\"message\":\"Muitas tentativas de login. Tente novamente em instantes.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private static class Bucket {
        private final AtomicInteger contador = new AtomicInteger(0);
        private volatile long inicioJanela = System.currentTimeMillis();

        synchronized boolean permitir() {
            long agora = System.currentTimeMillis();
            if (agora - inicioJanela > JANELA_MS) {
                contador.set(0);
                inicioJanela = agora;
            }
            return contador.incrementAndGet() <= LIMITE_REQUISICOES;
        }
    }
}