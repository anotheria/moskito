package net.anotheria.moskito.core.accumulation;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.02.14 14:44
 */
public class MSK156Test {
	public void testStackoverflow(){
		AccumulatorDefinition def = new AccumulatorDefinition();
		Accumulator acc = new Accumulator(def);

		int limit = 10;
		def.setAccumulationAmount(limit);

		//fill the list.
		for (int i=0; i<10; i++){
			acc.addValue(String.valueOf(i));
		}

		//this call shouldn't crash yet.
		acc.getValues();

		//fill the list until the sublist is too long, 2800 or more.
		for (int i=0; i<5600*2; i++){
			acc.addValue(String.valueOf(i));
		}

		//this call should produce stackoverflow.
		acc.getValues();
	}
}
