import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.topbraid.shacl.rules.RuleUtil;
import org.topbraid.shacl.validation.ValidationUtil;
import java.io.*; 

public class ShaclTest {

	public static void main(String[] args) {

		// The data
		Model data = ModelFactory.createDefaultModel();
		data.read("/home/alex/Desktop/calculate_distance/r2rml/output.ttl");

		// read function.ttl -- it creates a rule with JavaScript functionality to compute the
		// area of circles using its radius. But watch out, this code assume that all target nodes
		// hava a property radius. You need to add conditionals in the code
		Model function = ModelFactory.createDefaultModel();
		function.read("./resources/function.ttl");
		
		// You can add additional parameters, but you can consult the documentation
		// This model will only contain the inferred triples (via the rules)
		Model inferenced = RuleUtil.executeRules(data, function, null, null);
		inferenced.write(System.out, "TURTLE");
		 
		// Output to file aswell 
		 
		File file = new File("/home/alex/Desktop/calculate_distance/resources/output.ttl");
        try{
			
			FileOutputStream fop = new FileOutputStream(file);
			// fop.write(contentInBytes);
			inferenced.write(fop, "TURTLE");
			fop.flush();
			fop.close(); 

		}   
		
		catch(Exception e) {
			
			System.out.println("File not found!"); 
			
		} 
					
		// SHACL can also be used to validation. With the sh:Property I state that all
		// circles must have at exactly one radius
		// - uncomment the two lines above
		// - uncomment the circle without the radius in the data
		// - run the code below
		// - you will see that it complains about the one circle
		Resource report = ValidationUtil.validateModel(data, function, true);
		report.getModel().write(System.out, "TURTLE");
		
	}

}
