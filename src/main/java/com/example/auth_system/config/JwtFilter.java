package com.example.auth_system.config;

import com.example.auth_system.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Kiểm tra túi của khách (Lấy thông tin từ Header tên là "Authorization")
        String authHeader = request.getHeader("Authorization");

        // Quy ước quốc tế: Vé JWT luôn bắt đầu bằng chữ "Bearer " (khoảng trắng ở cuối)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // 2. Cắt bỏ 7 ký tự đầu ("Bearer ") để lấy ra đúng cái chuỗi vé giun dế
            String token = authHeader.substring(7);
            System.out.println("Vé gửi lên là: [" + token + "]");

            // 3. Đưa vé cho thợ soi xem có hợp lệ không
            if (jwtUtil.validateToken(token)) {
                // Vé xịn -> Lấy tên người dùng ra
                String username = jwtUtil.extractUsername(token);

                // 4. Báo với anh Bảo vệ: "Khách VIP đây, đóng mộc cho qua!"
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5. Mở rào chắn cho khách đi tiếp vào Controller (hoặc bị chặn lại nếu không có vé/vé dởm)
        filterChain.doFilter(request, response);
    }
}