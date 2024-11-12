package com.estsoft.oreumifancafe.domain.dto.board;


import com.estsoft.oreumifancafe.domain.board.Board;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBoardRequest {
    private Long id;
    private String title;
    private String content;
    private int boardType;
    private String boardCategoryName;
    private int state;

    public AddBoardRequest(Long id, String title, String content, int boardType, String boardCategoryName) {
        this.id =id;
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.boardCategoryName = boardCategoryName;;
    }
}
