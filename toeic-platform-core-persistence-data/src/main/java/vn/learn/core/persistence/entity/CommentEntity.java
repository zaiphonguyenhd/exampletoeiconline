package vn.learn.core.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "listenguidelineid")
    private ListenGuideLineEntity listenGuideLine;

    @Column(name = "createddate")
    private Timestamp createdDate;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserEntityId() {
        return user;
    }

    public void setUserEntityId(UserEntity userEntityId) {
        this.user = userEntityId;
    }

    public ListenGuideLineEntity getListenGuideLineEntityId() {
        return listenGuideLine;
    }

    public void setListenGuideLineEntityId(ListenGuideLineEntity listenGuideLineEntityId) {
        this.listenGuideLine = listenGuideLineEntityId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
