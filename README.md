# onevaletdevices

## Decision making reasoning
1. **Why not just parse the JSON file with Moshi instead of using Retrofit**
 >Although I could'just used Moshi to parse the json file I usef Retrofit with a Mock interceptor just to demonstrate I am capable of using the library.

2. **Error handling**
>I added a mock error handling case since I am not making any API requests, to test it, please just change the endpoint to anything other that "deviceslist"

3. **Passing a device object to the details screen**
> I decided on passing the device object as a Parcelable in order to simplify and demonstrate the knowledge of Parcelization and Serialization. In a production situation, it might be more suitable to only pass the ID and make a request to retreive detailed information of the Device.

### That's all! Thank you for the opportunity.
