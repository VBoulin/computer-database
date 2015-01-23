package com.excilys.formation.java.mapper;

import java.util.List;

public interface DtoMapper<X, Y> {
  
  /**
   * Creates a Dto X from an Y
   */
  X toDto(Y y);

  /**
   * Map an Y from the Dto X
   */
  Y fromDto(X x);
  
  /**
   * Map a list of Y into Dtos X
   */
  List<X> toDto(List<Y> y);

  /**
   * Map list of Dtos X to Objects Y
   */
  List<Y> fromDto(List<X> x);


}
