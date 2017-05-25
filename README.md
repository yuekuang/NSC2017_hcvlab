# NSC2017_hcvlab
Northampton Survival Center Data from 2017 April to mid May
### 05/25/2017 
We added `open_time`, `wait_open`, and `arrive_before_after` columns to the May dataset. `open_time` refers to the time that the survival center opened that day depending on the day of the week. `wait_open` refers to the waiting time in minutes, calculated by subtracting `open_time` from `time.arrived`. In the combined dataset, we calculated `income.per.person` by dividing `monthly.household.income` by `household_size`. We used that `income.per.person` to categorize the level of income into Low, LowMed, Med, MedHigh, and High, depending on the 20%, 40%, 60%, and 80% quartile of the values. We removed any pieces of data that seemed strange, such as Florence misspelled as Flonrece and a date value being in the `language.spoken.at.home` column. 

## 05/24/2017
There were some rows of data in the May dataset that the values of `time.arrived` and `intake.time` were switched, so we fixed those specific rows with Excel. We divided the combined data file into two different data sets, one for April and the other for May. For April, we got rid of the two columns `time.arrived` and `time_waited_minutes` because those values were not given in the dataset. 
