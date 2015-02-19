package com.excilys.formation.java.dto;

import java.util.List;

public interface DtoMapper<X, Y> {
  
  /**
   * Creates a Dto X from an Y
   * @param y 
   * @param format Date format
   * @return x
   */
  X toDto(Y y, String format);

  /**
   * Map an Y from the Dto X
   * @param x 
   * @param format Date format
   * @return y
   */
  Y fromDto(X x, String format);
  
  /**
   * Map a list of Y into Dtos X
   * @param y 
   * @param format Date format
   * @return x
   */
  List<X> toDto(List<Y> y, String format);

  /**
   * Map a list of Dtos X to Objects Y
   * @param x 
   * @param format Date format
   * @return y
   */
  List<Y> fromDto(List<X> x, String format);
}
