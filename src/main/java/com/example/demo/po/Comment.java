package com.example.demo.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nickname;
	private String email;
	private String content;
	private String avatar;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private boolean adminComment;
	
	@ManyToOne
	private Blog blog;
	
	@OneToMany(mappedBy = "parentComment")
	private List<Comment> replyComments = new ArrayList<>();
	
	@ManyToOne
	private Comment parentComment;
}
