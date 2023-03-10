package com.example.celog.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String fileOriginName;

    @Column(length = 500)
    private String fileUrl;

    public FileInfo(String fileOriginName, String fileUrl) {
        this.fileOriginName = fileOriginName;
        this.fileUrl = fileUrl;
    }

    public String S3key() {
        return fileUrl.substring(fileUrl.lastIndexOf(".com/") + 5);
    }
}