package quiz;
/*
 * Prints: R1 R4 R3 R2 H1 H2 
 */
class Raptor {
	static {
		System.out.print("R1 ");
	}

	public Raptor() {
		System.out.print("R2 ");
	}

	{
		System.out.print("R3 ");
	}
	static {
		System.out.print("R4 ");
	}
}

public class StaticTest extends Raptor {

	{
		System.out.print("H1 ");
	}

	public StaticTest() {
		System.out.print("H2 ");
	}

	public static void main(String[] args) {
		new StaticTest();
	}
}