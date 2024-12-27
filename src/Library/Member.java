package Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Library.DatabaseConnection;

public class Member {
    private String memberId;
    private String name;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }

    public static void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO members (member_id, name) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getMemberId());
            pstmt.setString(2, member.getName());
            pstmt.executeUpdate();
        }
    }

    public static List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(new Member(
                        rs.getString("member_id"),
                        rs.getString("name")
                ));
            }
        }
        return members;
    }
}
