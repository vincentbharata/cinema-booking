import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GrantTablespace {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "system";
        String password = "cinema123";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Connected to Oracle database");
            
            // Grant unlimited quota on USERS tablespace to CINEMA user
            String sql = "ALTER USER CINEMA QUOTA UNLIMITED ON USERS";
            stmt.execute(sql);
            System.out.println("✓ Granted UNLIMITED QUOTA ON USERS to CINEMA user");
            
            // Verify the grant
            var rs = stmt.executeQuery(
                "SELECT username, tablespace_name, max_bytes " +
                "FROM dba_ts_quotas WHERE username = 'CINEMA'"
            );
            
            System.out.println("\nVerification:");
            while (rs.next()) {
                System.out.println("  User: " + rs.getString(1) + 
                                 ", Tablespace: " + rs.getString(2) + 
                                 ", Max Bytes: " + rs.getLong(3));
            }
            
            System.out.println("\n✓ CINEMA user now has tablespace privileges");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
