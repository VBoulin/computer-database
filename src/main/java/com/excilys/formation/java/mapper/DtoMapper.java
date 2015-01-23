package com.excilys.formation.java.mapper;

import java.util.List;

import com.excilys.formation.java.model.Page;

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
   * Map a list of Dtos X to Objects Y
   */
  List<Y> fromDto(List<X> x);

  /**
   * Map a page of Y into Dtos X
   */
  Page<X> toDto(Page<Y> y);

  /**
   * Map a page of Dtos X to Objects Y
   */
  Page<Y> fromDto(Page<X> x);
  
  
}
