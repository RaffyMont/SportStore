function init(){
	const meno = document.getElementById("meno");
	if(meno)
		meno.disabled = true;
};

function cambiaQuantita(valore, stock)
{
    const input = document.getElementById("input_quantita");
    const meno = document.getElementById("meno");
    const piu = document.getElementById("piu");

    let val = parseInt(input.value);

    val += valore;

    if(val < 1)
        val = 1;

    if(val > stock)
        val = stock;

    input.value = val;

    meno.disabled = (val <= 1);
    piu.disabled = (val >= stock);
}