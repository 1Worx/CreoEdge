open_window_maximized yes

mapkey run_01 ~ Command `ProCmdModelOpen` ;\
mapkey(continued) ~ Trail `UI Desktop` `UI Desktop` `DLG_PREVIEW_POST` `file_open`;\
mapkey(continued) ~ Trail `UI Desktop` `UI Desktop` `PREVIEW_POPUP_TIMER` \
mapkey(continued) `file_open:Ph_list.Filelist:<NULL>`;\
mapkey(continued) ~ Update `file_open` `Inputname` `PUZZLE`;\
mapkey(continued) ~ Command `ProFileSelPushOpen_Standard@context_dlg_open_cmd`;

mapkey run_02 ~ Close `main_dlg_cur` `appl_casc`;\
mapkey(continued) ~ Command `ProCmdModelSaveAs` ;~ Open `file_saveas` `type_option`;\
mapkey(continued) ~ Close `file_saveas` `type_option`;\
mapkey(continued) ~ Select `file_saveas` `type_option` 1 `db_549`;\
mapkey(continued) ~ Activate `file_saveas` `OK`;~ Activate `UI Message Dialog` `ok`;\
mapkey(continued) ~ Update `export_slice` `ChordHeightPanel` `.01`;\
mapkey(continued) ~ FocusOut `export_slice` `ChordHeightPanel`;\
mapkey(continued) ~ Update `export_slice` `AngleControlPanel` `.5`;\
mapkey(continued) ~ Activate `export_slice` `AngleControlPanel`;\
mapkey(continued) ~ Move `export_slice` `export_slice` 2 42.635774 2.283596;\
mapkey(continued) ~ FocusOut `export_slice` `AngleControlPanel`;\
mapkey(continued) ~ Activate `export_slice` `Apply`;~ Activate `export_slice` `OK`;\
mapkey(continued) ~ Command `ProCmdWinCloseGroup` ;~ Command `ProCmdModelEraseNotDisp` ;\
mapkey(continued) ~ Activate `file_erase_nd` `ok_pb`;
file_open_default_folder working_directory
display shadewithedges
display_annotations no
display_axes no
display_coord_sys no
display_planes no
display_points no
spin_center_display no
