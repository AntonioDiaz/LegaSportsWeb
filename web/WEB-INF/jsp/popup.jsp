<!-- Modal -->
<script type="text/javascript">
	function showDialogAlert(bodyText) {
		$('#modalContent').html(bodyText);
		$('#myModalAlert').modal('show');
		 $("#buttons_alert").show();
		 $("#buttons_confirm").hide();
	}
	
	function showDialogConfirm(bodyText, functionAccept) {
		$('#modalContent').html(bodyText);
		$('#myModalAlert').modal('show');
		$("#buttons_alert").hide();
		$("#buttons_confirm").show();
		$('#button_accept').off('click');
		$("#button_accept").click(functionAccept);
	}

</script>
<div id="myModalAlert" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm" style="width: 40%">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header modal-header-munisports">
				<button type="button" class="close close-munisports" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="modalTitle">MuniSports 2017</h4>
			</div>
			<div class="modal-body">
				<div id="modalContent"></div>
			</div>
			<div class="modal-footer">
				<div id="buttons_alert">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
				<div id="buttons_confirm">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="button_accept" type="button" class="btn btn-default" data-dismiss="modal">Aceptar</button>
				</div>
			</div>
		</div>
	</div>
</div>	