package com.sparta.delivery.menu.entity;

import com.sparta.delivery.global.entity.BaseEntity;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Setter
@Table(name = "menus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("1")
    private int price;

    @Column(length = 500)
    private String description; //설명

    @Column(nullable = false)
    private boolean deleted = false; // 삭제여부 soft del

    // 사장 1:N 메뉴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    public Menu(String name, int price, String description, User owner) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.owner = owner;
    }

    // update

    public void deleted(){
        this.deleted = true;
    }

}
