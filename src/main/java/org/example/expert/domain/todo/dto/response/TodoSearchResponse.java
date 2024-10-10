package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoSearchResponse {

    private final Long id;
    private final String title;         // 일정에 대한 모든 정보가 아닌, 제목만
    private final long assigneeCount;   // 해당 일정의 담당자 수
    private final long commentCount;    // 해당 일정의 총 댓글 갯수


    public TodoSearchResponse(Long id, String title, long assigneeCount, long commentCount) {
        this.id = id;
        this.title = title;
        this.assigneeCount = assigneeCount;
        this.commentCount = commentCount;
    }
}
