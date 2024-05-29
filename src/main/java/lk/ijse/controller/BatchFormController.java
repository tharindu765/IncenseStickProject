    package lk.ijse.controller;
    
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.input.KeyEvent;
    import lk.ijse.Util.Regex;
    import lk.ijse.model.Batch;
    import lk.ijse.model.Order;
    import lk.ijse.model.tm.BatchTm;
    import lk.ijse.model.tm.OrderTm;
    import lk.ijse.repository.BatchRepo;
    import lk.ijse.repository.OrderRepo;
    
    import java.sql.Date;
    import java.sql.SQLException;
    import java.util.List;
    
    public class BatchFormController {
        public TableView<BatchTm> tblBatch;
        public TableColumn<?,?> colBatchID;
        public TableColumn<?,?> colDate;
        public TableColumn<?,?> colQty;
        public TextField txtBatch;
        public TextField txtQty;
        public DatePicker txtdate;
    
        public void initialize() {
            setCellValueFactor();
            loadAllBatches();
            tblBatch.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    txtBatch.setText(String.valueOf(newSelection.getBatchID()));
                    txtdate.setValue(newSelection.getDate().toLocalDate());
                    txtQty.setText(String.valueOf(newSelection.getQty()));
                } else {
                    clearFields();
                }
            });
        }
        private void clearFields() {
            txtBatch.clear();
            txtdate.setValue(null);
            txtQty.clear();
        }

    
        private void loadAllBatches() {
            ObservableList<BatchTm> obList = FXCollections.observableArrayList();
            try{
                List<Batch> batchList = BatchRepo.getAll();
                for(Batch o:batchList){
                    BatchTm BatchTm = new BatchTm(
                            o.getBatchID(),
                            o.getDate(),
                            o.getQty()
                    );
                    obList.add(BatchTm);
                }
                tblBatch.setItems(obList);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
    }
    
        private void setCellValueFactor() {
            colBatchID.setCellValueFactory(new PropertyValueFactory<>("BatchID"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        }
               public void dateAction(ActionEvent actionEvent) {
        }
        public void btnSave(ActionEvent actionEvent) {
            String batchIDText = txtBatch.getText();
            String date = String.valueOf(txtdate.getValue());

            if (batchIDText.isEmpty() || date.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill in all fields.").showAndWait();
                return;
            }
            if (isValied()) {
                try {
                    int batchID = Integer.parseInt(batchIDText);
                    Date sdate = Date.valueOf(date);
                    Batch batch = new Batch(batchID, sdate);
                    boolean saved = BatchRepo.save(batch);
                    if (saved) {
                        loadAllBatches();
                        txtBatch.clear();
                        txtQty.clear();
                        new Alert(Alert.AlertType.INFORMATION, "Batch saved successfully.").showAndWait();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to save batch.").showAndWait();
                    }
                } catch (NumberFormatException | SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "An error occurred. Please try again. " + e.getMessage()).showAndWait();
                }
            }
        }
        public void btnUpdate(ActionEvent actionEvent) {
            BatchTm selectedBatch = tblBatch.getSelectionModel().getSelectedItem();
            if (selectedBatch == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a batch to update.").showAndWait();
                return;
            }

            String batchIDText = txtBatch.getText();
            String date = String.valueOf(txtdate.getValue());

            if (batchIDText.isEmpty() || date.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill in all fields.").showAndWait();
                return;
            }

            try {
                int batchID = Integer.parseInt(batchIDText);
                Date sdate = Date.valueOf(date);
                selectedBatch.setBatchID(batchID);
                selectedBatch.setDate(sdate);
                boolean updated = BatchRepo.update(selectedBatch);
                if (updated) {
                    loadAllBatches();
                    txtBatch.clear();
                    txtQty.clear();
                    new Alert(Alert.AlertType.INFORMATION, "Batch updated successfully.").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update batch.").showAndWait();
                }
            } catch (NumberFormatException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred. Please try again.").showAndWait();
            }
        }


        public void btnDelete(ActionEvent actionEvent) {
        }

        public void btnClear(ActionEvent actionEvent) {
            txtBatch.clear();
            txtQty.clear();
            txtdate.setValue(null);
            tblBatch.getSelectionModel().clearSelection();
        }
        public void btnSearch(ActionEvent actionEvent) {
            String batchIDText = txtBatch.getText();
            Date date = Date.valueOf(txtdate.getValue());
            String qtyText = txtQty.getText();

            try {
                List<Batch> searchResults = BatchRepo.searchBatches(batchIDText, date, qtyText);
                ObservableList<BatchTm> obList = FXCollections.observableArrayList();
                for (Batch batch : searchResults) {
                    BatchTm batchTm = new BatchTm(
                            batch.getBatchID(),
                            batch.getDate(),
                            batch.getQty()
                    );
                    obList.add(batchTm);
                }
                tblBatch.setItems(obList);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error occurred while searching batches").showAndWait();
            }
        }

        public void txtBatchIDActionRelease(KeyEvent keyEvent) {
            Regex.setTextColor(lk.ijse.Util.TextField.BATCHID,txtBatch);
        }

        public void txtQtyActionRelease(KeyEvent keyEvent) {
            Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty);
        }
        public boolean isValied() {
            if (!Regex.setTextColor(lk.ijse.Util.TextField.BATCHID,txtBatch)) return false;
            if (!Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty)) return false;
            return true;
        }
    }
    
