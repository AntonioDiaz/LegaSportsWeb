const MATCH_STATE_PENDING = 0;
const MATCH_STATE_PLAYED = 1;
const MATCH_STATE_CANCELED = 2;
const DATE_FORMAT = "DD/MM/YYYY HH:mm";

function download_csv(csv, filename) {
	var csvFile;
	var downloadLink;

	// CSV FILE
	csvFile = new Blob([csv], {type: "text/csv"});

	// Download link
	downloadLink = document.createElement("a");

	// File name
	downloadLink.download = filename;

	// We have to create a link to the file
	downloadLink.href = window.URL.createObjectURL(csvFile);

	// Make sure that the link is not displayed
	downloadLink.style.display = "none";

	// Add the link to your DOM
	document.body.appendChild(downloadLink);

	// Lanzamos
	downloadLink.click();
}

function export_table_to_csv(idTable, filename) {
	var csv = [];
	var rows = document.querySelectorAll("#" + idTable + " tr");

	for (var i = 0; i < rows.length; i++) {
		var row = [], cols = rows[i].querySelectorAll("td, th");

		for (var j = 0; j < cols.length; j++)
			row.push(cols[j].innerText);

		csv.push(row.join(","));
	}

	// Download CSV
	download_csv(csv.join("\n"), filename);
}