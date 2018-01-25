package com.adiaz.utils.initcompetition;

import com.adiaz.utils.LocalSportsConstants;
import lombok.Data;

@Data
public class MatchInput {
    String dateStr;
    String courtFullNameStr;
    String teamLocalStr;
    String teamVisitorStr;
    Integer scoreLocal;
    Integer scoreVisitor;
    LocalSportsConstants.MATCH_STATE state;
}
