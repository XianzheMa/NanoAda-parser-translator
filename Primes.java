public class Primes{
	public static void main(final String[] args) {
		Mprimes();
	}
	
	private static void Mprimes() {
		int Tnum, Tj, Tcount;
		System.out.println("");
		Tnum = 1;
		while(Tnum <= 100){
			if(Tnum == 1){
				System.out.print(0);
			}
			else{
				Tj = 1;
				Tcount = 0;
				while(Tj <= Tnum){
					if(Tnum % Tj != 0){
						Tcount = Tcount + 1;
					}
					Tj = Tj + 1;
				}
				if(Tcount == Tnum - 2){
					System.out.print(",1");
				}
				else{
					System.out.print(",0");
				}
			}
			Tnum = Tnum + 1;
		}
		System.out.println("");
	}
}