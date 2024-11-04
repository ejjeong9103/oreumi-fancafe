package com.estsoft.oreumifancafe.domain.dto.board;


import com.estsoft.oreumifancafe.domain.board.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBoardRequest {
    private String title;
    private String content;
    private int boardType;
    private String boardCategoryName;
    private int state;
}
