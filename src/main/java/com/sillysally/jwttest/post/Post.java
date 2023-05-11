package com.sillysally.jwttest.post;

import com.sillysally.jwttest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.MonthDay;


@Data //Getters, Setters, ToString, ArgsConstructor osv.
@Builder //Helps build object, design patterns
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_user")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    private String postMessage;
    private MonthDay createdAt;
    private String typeOfHelp;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "_user", nullable = false)
    private User user;


}
