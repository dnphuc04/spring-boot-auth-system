package com.example.auth_system.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ĐÂY LÀ CHÌA KHÓA BÍ MẬT CỦA BẠN (Phải dài và khó đoán)
    // Nếu ai ăn cắp được chuỗi này, họ có thể tự in vé giả!
    private static final String SECRET_KEY = "BiMatCuaPhucKhongTheTietLoChoAiBietDuoc123456789!@#";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Hàm này làm nhiệm vụ NẶN RA CÁI VÉ
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // Ghi tên khách lên vé
                .claim("role", role) // thêm role vào bụng vé
                .setIssuedAt(new Date()) // Ghi ngày giờ phát hành
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Hạn sử dụng vé: 1 ngày
                .signWith(key, SignatureAlgorithm.HS256) // Lấy chìa khóa bí mật đóng dấu cái cộp!
                .compact(); // Đóng gói lại thành chuỗi String
    }

    // Hàm bóc vé lấy tên người dùng (username)
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Hàm kiểm tra vé xem có hợp lệ không (Đúng con dấu, chưa hết hạn)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // Vé xịn
        } catch (Exception e) {
            System.out.println("Lỗi soi vé JWT: " + e.getMessage());
            return false; // Vé giả hoặc đã hết hạn
        }
    }

    // Hàm mới: Moi bụng vé ra xem có ghi chữ ADMIN hay USER
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}