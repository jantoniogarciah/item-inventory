package com.exercise.jagh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.exercise.jagh.exception.ResourceNotFoundException;
import com.exercise.jagh.model.Item;
import com.exercise.jagh.repository.ItemRepository;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/item")
@Api(value="Inventory maintenance", description="Operations pertaining of an inventory maintenance")
public class ItemController {
 @Autowired
 private ItemRepository itemRepository;

 @ApiOperation(value = "List of inventory items list", response = List.class)
 @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
 @GetMapping("/1itemInventory")
 public List<Item> getAllItems() {
  return itemRepository.findAll();
 }

 @ApiOperation(value = "Read item details (by item no)")
 @GetMapping("/2itemInventory/{itemNo}")
 public ResponseEntity<Item> getItemById(@ApiParam(value = "Item No from which item object will retrieve", required = true)
		 @PathVariable(value = "itemNo") Long itemNo)
   throws ResourceNotFoundException {
  Item item = itemRepository.findById(itemNo)
    .orElseThrow(() -> new ResourceNotFoundException("Item not found for this item No :: " + itemNo));
  return ResponseEntity.ok().body(item);
 }
 
 @ApiOperation(value= "Withdrawal quantity from an item")
 @GetMapping("/3itemWithdrawal/{itemNo}")
 public ResponseEntity<Item> withdrawalAmount(@PathVariable Long itemNo, @ApiParam(name="amountWithdrawal", value="Amount to withdrawal") @RequestParam("amountWithdrawal") Integer amount) throws ResourceNotFoundException {
	 
	 Item item = itemRepository.findById(itemNo)
			    .orElseThrow(() -> new ResourceNotFoundException("Item not found for this itemNo :: " + itemNo));

	 int result = item.getItemAmount() - amount.intValue();
	 if(result<0) {
		 result = 0;
	 }
			  item.setItemAmount(result);
			  final Item updatedItem = itemRepository.save(item);
			  return ResponseEntity.ok(updatedItem); 
 }
 
 @ApiOperation(value= "Adding quantity from an item")
 @GetMapping("/4itemAdding/{itemNo}")
 public ResponseEntity<Item> addAmount(@PathVariable Long itemNo, @ApiParam(name="amountAdd", value="Amount to add") @RequestParam("amountAdd") Integer amount) throws ResourceNotFoundException {
	 
	 Item item = itemRepository.findById(itemNo)
			    .orElseThrow(() -> new ResourceNotFoundException("Item not found for this itemNo :: " + itemNo));

	 int result = item.getItemAmount() + amount.intValue();
	 if(result<0) {
		 result = 0;
	 }
			  item.setItemAmount(result);
			  final Item updatedItem = itemRepository.save(item);
			  return ResponseEntity.ok(updatedItem); 
 }
 
 @ApiOperation(value= "Adding item to stock")
 @PostMapping("/5addItem")
 public Item createItem(@Valid @RequestBody Item item) {
  return itemRepository.save(item);
 }
 
 @ApiOperation(value= "Delete item")
 @DeleteMapping("/6itemDeletion/{itemNo}")
 public Map<String, Boolean> deleteItem(@PathVariable(value = "itemNo") Long itemNo)
   throws ResourceNotFoundException {
  Item item = itemRepository.findById(itemNo)
    .orElseThrow(() -> new ResourceNotFoundException("Item not found for this itemNo :: " + itemNo));

  itemRepository.delete(item);
  Map<String, Boolean> response = new HashMap<>();
  response.put("deleted", Boolean.TRUE);
  return response;
 }

 @ApiOperation(value= "Update a complete item")
 @PutMapping("/7itemUpdate/{itemNo}")
 public ResponseEntity<Item> updateItem(@PathVariable(value = "itemNo") Long itemNo,
   @Valid @RequestBody Item itemDetails) throws ResourceNotFoundException {
  Item item = itemRepository.findById(itemNo)
    .orElseThrow(() -> new ResourceNotFoundException("Item not found for this itemNo :: " + itemNo));

  item.setItemName(itemDetails.getItemName());
  item.setItemAmount(itemDetails.getItemAmount());
  item.setItemInventoryCode(itemDetails.getItemInventoryCode());
  final Item updatedItem = itemRepository.save(item);
  return ResponseEntity.ok(updatedItem);
 }

 
}