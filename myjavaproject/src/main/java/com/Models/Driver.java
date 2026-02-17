 package com.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "drivers") // הגדרת שם הטבלה במסד הנתונים
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // מפתח ראשי רץ אוטומטית (PK)
    private Long id;

    @Column(nullable = false) // שדה חובה
    private String name;

    private String phone;

    private double rating; // דירוג הנהג 

    // קשר של "אחד לרבים" - נהג אחד יכול לבצע הרבה נסיעות
    // הערה: נממש את הצד של ה-Trip בהמשך
    @OneToMany(mappedBy = "driver")
    
    private List<Ride> rides;

    // קונסטרקטור ריק (חובה עבור JPA)
    public Driver() {
    }

    // קונסטרקטור נוחות ליצירת נהג חדש
    public Driver(String name, String phone, double rating) {
        this.name = name;
        this.phone = phone;
        this.rating = rating;
    }

    // Getters and Setters (נחוצים כדי ש-Spring יוכל לעבוד עם הנתונים)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
