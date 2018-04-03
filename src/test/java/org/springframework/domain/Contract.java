/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.multichain.annotation.MultiChainStream;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a contract between a buyer and a seller.
 */
@MultiChainStream("contract")
public class Contract
{
  @Id
  private String id;

  private String buyer;
  private String seller;

  private Date endDate;
  private Date startDate;

  private BigDecimal price;

  /**
   * Default constructor; required for serialization libraries to create
   * instances.
   */
  public Contract()
  {
  }

  /**
   * Creates a contract.
   *
   * @param id        The unique identifier for the contract.
   * @param buyer     The name of the buyer.
   * @param seller    The name of the seller.
   * @param startDate The date on which the contract starts.
   * @param endDate   The date on which the contract ends.
   * @param price     The price agreed between the buyer and the seller as
   *                  part of the contract.
   */
  public Contract(final String id
      , final String buyer
      , final String seller
      , final Date startDate
      , final Date endDate
      , final BigDecimal price)
  {
    this.buyer = buyer;
    this.endDate = endDate;
    this.id = id;
    this.price = price;
    this.seller = seller;
    this.startDate = startDate;
  }

  /**
   * Gets the name of the buyer.
   *
   * @return The name of the buyer.
   */
  public String getBuyer()
  {
    return buyer;
  }

  /**
   * Gets the date on which the contract ends.
   *
   * @return The date on which the contract ends.
   */
  public Date getEndDate()
  {
    return endDate;
  }

  /**
   * Gets the unique contract identifier.
   *
   * @return The unique contract identifier.
   */
  public String getId()
  {
    return id;
  }

  /**
   * Gets the price agreed between the buyer and the seller as part of the
   * contract.
   *
   * @return The price agreed between the buyer and the seller as part of the
   * * contract.
   */
  public BigDecimal getPrice()
  {
    return price;
  }

  /**
   * Gets the name of the seller.
   *
   * @return The name of the seller.
   */
  public String getSeller()
  {
    return seller;
  }

  /**
   * Gets the date on which the contract starts.
   *
   * @return The date on which the contract starts.
   */
  public Date getStartDate()
  {
    return startDate;
  }
}
