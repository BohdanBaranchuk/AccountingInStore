# AccountingInStore
The system of accounting of goods in stock.

All necessary information about java source code can be found in the generated Javadocs in the attached folder.  
This program aims to account the goods in the shop.

To run the application install the database on your PC. Please use MySQL database. Create new MySQL connection. 
Create new schema. Then enter connection parameters in the hibernate-annotation.cfg.xml. Run the application.

Press the button “Добавить” to add new entry (record) in the database. Each entry must have unique name. Fill the necessary forms.
Be careful! Please enter valid values. For example, in the form “Закупочная стоимость” you can enter only numbers.
For each good you can add up to 5 images. To add the image press the button “Добавить/Изменить”. The file chooser will open.
You can select only image formats (.jpg, .ipeg, .png.,  tif., .tiff, .gif). There is a preview window in the file chooser. Select the image and press the button OK.
To add a new good press “Добавить” or press “Отмена” to cancel changes. If all parameters are true, the new entry will be added. 
If you want to see the entries from the database, set the search criteria and press “Поиск”.  

All received data will be shown in the table. If you want to view the images of some good select the entry in the table and 
all available images will be shown in the upper right of the app. 

To change some good select it in the table and press the button “Изменить”. 

To delete the good select it in the table and press the button “Удалить”. Confirm your action in the confirm dialog. 

