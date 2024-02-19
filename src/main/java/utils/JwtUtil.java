package utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

/**
 * header 跟 payload 分別用 url safe base64 加密。
 * base64(header).base64(payload) 將加密後的字串組成signature，利用secret作為密鑰，使用HMAC-SHA256進行雜湊加密。
 * 最後組成JWT base64(header).base64(payload).base64(sha256(signature))
 */
public class JwtUtil {
    private static final String TOKEN_SECRET = "NDllYmVmYWMtNDgzMy00NWM4LTlmOTYtZmIzM2VjMjMyYWY2";
    private static final long EXPIRE_TIME = System.currentTimeMillis() + (5 * 60 * 1000);

    private JwtUtil() {
    }

    public static JwtUtil jwtUtil;

    public static synchronized JwtUtil getInstance() {
        return  jwtUtil == null ? new JwtUtil() : jwtUtil;
    }

    private String createJwt() {
        return Jwts.builder()
                .signWith(generalKey(), SignatureAlgorithm.HS256)
                .setHeaderParam("typ", "JWT")
                .setIssuer("COLINE")
                .setExpiration(new Date(EXPIRE_TIME))
                .compact();
    }

    /**
     * 官方給的解析JWS流程：
     * <p>
     * 1.使用該Jwts.parserBuilder()方法創建一個JwtParserBuilder實例。
     * 2.指定要用於驗證 JWS 簽名的SecretKey或非對稱的PublicKey
     * 3.調用的build()方法JwtParserBuilder以返迴JwtParser。
     * 4.最後，parseClaimsJws(String)使用您的 jws 調用該方法String，生成原始 JWS。
     * 5.整個調用被包裝在一個 try/catch 塊中，以防解析或簽名驗證失敗。
     */
    public Jws<Claims> parseJWT(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generalKey())
                    .build()
                    .parseClaimsJws(jwt);
        } catch (MalformedJwtException e) {
            //畸形的 token，可能長度不足等原因
            System.out.printf("malformed token error: %s\n", e.getMessage());
        } catch (SignatureException e) {
            //也就是說拿去驗證 token 的 key 跟當初簽署的 key 是不一樣的，因此會錯
            System.out.printf("secret key is not right error: %s\n", e.getMessage());
        } catch (UnsupportedJwtException e) {
            //token 裡面的 header 欄位的 alg 不是採用 HS256，因此報錯
            System.out.printf("alg is not right error: %s\n", e.getMessage());
        } catch (MissingClaimException e) {
            //Claim 裡面缺少了ㄧ些欄位，因此報錯
            System.out.printf("missing some claim field error: %s\n", e.getMessage());
        } catch (IncorrectClaimException e) {
            //Claim 裡面的一些欄位的值不符合特定的值。
            System.out.printf("claim field value is not right: %s\n", e.getMessage());
        } catch (Exception e) {
            System.out.println("解析失敗:" + e);
            e.printStackTrace();
        }
        return null;
    }

    private Key generalKey() {
        return Keys.hmacShaKeyFor(TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        String jwt = JwtUtil.getInstance().createJwt();
        System.out.println("createJwt : " + jwt);
        Jws<Claims> jws = JwtUtil.getInstance().parseJWT(jwt);
        System.out.println("parseJWT jws : " + jws);

        String webhookJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDT0xJTkUiLCJleHAiOjE3MDgwNjM4NjQ0NjF9.gIsmKbkP8EIXT8zBTnyPMeupyNWLq246liKTkU9xTS4";
        Jws<Claims> webhookJws = JwtUtil.getInstance().parseJWT(webhookJwt);
        System.out.println("parseJWT webhookJws : " + webhookJws);
    }
}
