var portInput = document.getElementById("port");
var endpointInput = document.getElementById("endpoint");
var currencyInput = document.getElementById("currency");
var secondParamInput = null;
var UrlSpan = document.getElementById("URL");
var button = document.getElementById("button");
var responseSpan = document.getElementById("response");
var codeSpan = document.getElementById("code");

var secondParamRow = document.getElementById("second-param");

function constructSecondParameterRow() {
	if(endpointInput.value == "rate") {
		secondParamRow.innerHTML = /* html */ `
			<td>
				<h3>date:</h3>
			</td>
			<td>
				<input type="date" id="dateInput">
			</td>
		`;
		secondParamInput = document.getElementById("dateInput");
		secondParamInput.valueAsDate = new Date();

	}
	else {
		secondParamRow.innerHTML = /* html */ `
			<td>
				<h3>quotations count:</h3>
			</td>
			<td>
				<input type="number" id="numberInput" value="10">
			</td>
		`;
		secondParamInput = document.getElementById("numberInput");
	}

	secondParamInput.addEventListener("input", constructURL);
}

function constructURL() {
	codeSpan.innerHTML = "";
	responseSpan.innerHTML = "";

	UrlSpan.innerHTML = 
	"http://localhost:"
	+ portInput.value
	+ "/" + endpointInput.value
	+ "/" + currencyInput.value
	+ "/" + secondParamInput.value;
}

portInput.addEventListener("input", constructURL);
currencyInput.addEventListener("input", constructURL);

endpointInput.addEventListener("input", (event) => {
	constructSecondParameterRow();
	constructURL();
});

button.addEventListener("click", (event) => {
	fetch(UrlSpan.innerHTML)
		.then((response) => {
			codeSpan.innerHTML = response.status;
			return response.text();
		})
		.then((text) => responseSpan.innerHTML = text);
})

constructSecondParameterRow();
constructURL();