package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
// {"e":"trade","E":1678868445257,"s":"BTCUSDT","t":2973567800,"p":"24878.00000000","q":"0.08393000","b":20112029681,"a":20112029682,"T":1678868445256,"m":true,"M":true}

@Data
public class TradeDTO {
	@JsonProperty("s")
	private String symbol;
	@JsonProperty("p")
	private String price;
	@JsonProperty("q")
	private String quantity;
	@JsonProperty("b")
	private long bid;
	@JsonProperty("a")
	private String ask;
}
