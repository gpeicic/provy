package com.example.provy.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BucketRateLimitFilter implements Filter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket newBucket(){
        return Bucket.builder()
                .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofSeconds(5))))
                .build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String ip = request.getRemoteAddr();

        Bucket bucket = cache.computeIfAbsent(ip, k-> newBucket());
        if(bucket.tryConsume(1)){
            chain.doFilter(request,response);
        }else {
            HttpServletResponse http = (HttpServletResponse) response;
            http.setStatus(429);
            http.getWriter().write("Rate limit exceeded!");
        }
    }


}
