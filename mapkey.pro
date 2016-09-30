mapkey zz ~ Command `ProCmdModelOpen` ;\
mapkey(continued) ~ Trail `UI Desktop` `UI Desktop` `DLG_PREVIEW_POST` `file_open`;\
mapkey(continued) ~ Select `file_open` `Ph_list.Filelist` 1 `puzzle.prt`;\
mapkey(continued) ~ Activate `file_open` `Ph_list.Filelist` 1 `puzzle.prt`;\
mapkey(continued) ~ Trail `UI Desktop` `UI Desktop` `PREVIEW_POPUP_TIMER` \
mapkey(continued) `file_open:Ph_list.Filelist:<NULL>`;\
mapkey(continued) ~ Activate `main_dlg_cur` `page_Tools_control_btn` 1;\
mapkey(continued) ~ Command `ProCmdMmParams` ;\
mapkey(continued) ~ Move `relation_dlg` `relation_dlg` 2 7.390029 6.076246;\
mapkey(continued) ~ Arm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowA` `value`;\
mapkey(continued) ~ Disarm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowA` `value`;\
mapkey(continued) ~ Select `relation_dlg` `ParamsPHLay.ParTable` 2 `rowA` `value`;\
mapkey(continued) ~ Key `relation_dlg` `ParamsPHLay.ParTable` 540016688 `0`;\
mapkey(continued) ~ Update `relation_dlg` `ParamsPHLay.ParTable_INPUT` `0`;\
mapkey(continued) ~ Arm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowB` `value`;\
mapkey(continued) ~ Disarm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowB` `value`;\
mapkey(continued) ~ Select `relation_dlg` `ParamsPHLay.ParTable` 2 `rowB` `value`;\
mapkey(continued) ~ Key `relation_dlg` `ParamsPHLay.ParTable` 540016688 `9`;\
mapkey(continued) ~ Update `relation_dlg` `ParamsPHLay.ParTable_INPUT` `0`;\
mapkey(continued) ~ Arm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowC` `value`;\
mapkey(continued) ~ Disarm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowC` `value`;\
mapkey(continued) ~ Select `relation_dlg` `ParamsPHLay.ParTable` 2 `rowC` `value`;\
mapkey(continued) ~ Key `relation_dlg` `ParamsPHLay.ParTable` 540016688 `0`;\
mapkey(continued) ~ Update `relation_dlg` `ParamsPHLay.ParTable_INPUT` `0`;\
mapkey(continued) ~ Arm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowD` `value`;\
mapkey(continued) ~ Disarm `relation_dlg` `ParamsPHLay.ParTable` 2 `rowD` `value`;\
mapkey(continued) ~ Select `relation_dlg` `ParamsPHLay.ParTable` 2 `rowD` `value`;\
mapkey(continued) ~ Key `relation_dlg` `ParamsPHLay.ParTable` 540016688 `0`;\
mapkey(continued) ~ Update `relation_dlg` `ParamsPHLay.ParTable_INPUT` `5`;\
mapkey(continued) ~ Activate `relation_dlg` `PB_OK`;~ Command `ProCmdRegenPart` ;\
mapkey(continued) ~ Command `ProCmdModelSave` ;~ Close `main_dlg_cur` `appl_casc`;\
mapkey(continued) ~ Command `ProCmdModelSaveAs` ;~ Open `file_saveas` `type_option`;\
mapkey(continued) ~ Close `file_saveas` `type_option`;\
mapkey(continued) ~ Select `file_saveas` `type_option` 1 `db_552`;\
mapkey(continued) ~ Open `file_saveas` `type_option`;~ Close `file_saveas` `type_option`;\
mapkey(continued) ~ Select `file_saveas` `type_option` 1 `db_549`;\
mapkey(continued) ~ Activate `file_saveas` `OK`;~ Activate `UI Message Dialog` `ok`;\
mapkey(continued) ~ Update `export_slice` `ChordHeightPanel` `.5`;\
mapkey(continued) ~ FocusOut `export_slice` `ChordHeightPanel`;\
mapkey(continued) ~ Update `export_slice` `AngleControlPanel` `.5`;\
mapkey(continued) ~ FocusOut `export_slice` `AngleControlPanel`;\
mapkey(continued) ~ Update `export_slice` `StepSizePanel` `1`;\
mapkey(continued) ~ Activate `export_slice` `StepSizePanel`;\
mapkey(continued) ~ Move `export_slice` `export_slice` 2 27.863148 1.532747;\
mapkey(continued) ~ FocusOut `export_slice` `StepSizePanel`;~ Activate `export_slice` `Apply`;\
mapkey(continued) ~ Activate `export_slice` `OK`;~ Command `ProCmdWinCloseGroup` ;\
mapkey(continued) ~ Command `ProCmdModelEraseNotDisp` ;~ Activate `file_erase_nd` `ok_pb`;
