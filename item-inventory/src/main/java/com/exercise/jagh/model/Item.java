package com.exercise.jagh.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

 private long itemNo;
 private String itemName;
 private int itemAmount;
 private String itemInventoryCode;
 
 public Item() {
  
 }
 
 public Item(String itemName, int itemAmount, String itemInventoryCode) {
  this.itemName = itemName;
  this.itemAmount = itemAmount;
  this.itemInventoryCode = itemInventoryCode;
 }
 
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 public long getItemNo() {
  return itemNo;
 }
 public void setItemNo(long itemNo) {
  this.itemNo = itemNo;
 }
 
 @Column(name = "itemName", nullable = false)
 public String getItemName() {
  return itemName;
 }
 public void setItemName(String itemName) {
  this.itemName = itemName;
 }
 
 @Column(name = "itemAmount", nullable = false)
 public int getItemAmount() {
  return itemAmount;
 }
 public void setItemAmount(int itemAmount) {
  this.itemAmount = itemAmount;
 }
 
 @Column(name = "itemInventoryCode", nullable = false)
 public String getItemInventoryCode() {
  return itemInventoryCode;
 }
 public void setItemInventoryCode(String itemInventoryCode) {
  this.itemInventoryCode = itemInventoryCode;
 }

 @Override
 public String toString() {
  return "Item [itemNo=" + itemNo + ", itemName =" + itemName + ", itemAmount =" + itemAmount + ", itemInventoryCode=" + itemInventoryCode
    + "]";
 }
 
}