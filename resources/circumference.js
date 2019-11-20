var NS = "http://www.example.com/people/data/";
var previous_prices = []; 

function color($this) {
	var price_property  = getProperty($this, "Price");
	var current_price = price_property.lex; 
    var last_price = previous_prices.pop(); 
    if (current_price > last_price) {
		
		var result  =  TermFactory.literal("decreased", "^^xsd:string");

	}
	else{
		
		var result  =  TermFactory.literal("increased", "^^xsd:string");
	}
	// Create a triple
	// Note: it is important to use $this for the subject :-)
	previous_prices.push(current_price); 
	return [ [$this, TermFactory.namedNode(NS + "ValueChange"), result] ] ;
}

function getProperty($this, name) {
	var it = $data.find($this, TermFactory.namedNode(NS + name), null);
	var result = it.next().object;
	it.close();
	print("result ", result); 
	return result;
}


