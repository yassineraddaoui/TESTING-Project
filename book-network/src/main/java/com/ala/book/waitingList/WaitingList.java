package com.ala.book.book;

import com.ala.book.common.BaseEntity;
import com.ala.book.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WaitingList extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
}
