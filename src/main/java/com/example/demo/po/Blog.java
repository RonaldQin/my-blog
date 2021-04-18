package com.example.demo.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "t_blog")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private String content;
	private String firstPicture;
	private String flag;
	private Integer views;
	private boolean appraciation;
	private boolean shareStatement;
	private boolean commentable;
	private boolean published;
	private boolean recommend;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@ManyToOne
	private Type type;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})
	private List<Tag> tags = new ArrayList<>();
	
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();
	
	@Transient // 字段不做持久化
	private String tagIds;
}
