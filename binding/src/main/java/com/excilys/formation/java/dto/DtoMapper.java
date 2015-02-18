package com.excilys.formation.java.dto;

import java.util.List;

public interface DtoMapper<X, Y> {
  
  /**
   * Creates a Dto X from an Y
   */
  X toDto(Y y, String format);

  /**
   * Map an Y from the Dto X
   */
  Y fromDto(X x, String format);
  
  /**
   * Map a list of Y into Dtos X
   */
  List<X> toDto(List<Y> y, String format);

  /**
   * Map a list of Dtos X to Objects Y
   */
  List<Y> fromDto(List<X> x, String format);
}
