How would you improve your solution? What would be the next steps?
- I would improve the data structures defined to account for concurrency issues. 
The models would be closer to some database entities and would treat the whole transfer process as a transaction.
- Would make the rules for the different transactions more easly configurable.
- Would implement retry for failed transactions if there's no apparent cause of error.

After implementation, is there any design decision that you would have done different?
 Maybe implement a more specific factory pattern that would infere the type of transaction based on different business rules.


How would you adapt your solution if transfers are not instantaneous?
- Transfers could be queued by a middleware, or in a thread safe data structure to operate as a Queue.
