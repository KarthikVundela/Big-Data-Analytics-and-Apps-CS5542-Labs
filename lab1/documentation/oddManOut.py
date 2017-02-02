
def oddManOut(array):
    '''to find out the integer which is present only once in a given array'''
    frequency = {}#dictionary for storing integer:count
    #set count=1 if the integer is not repeated in the previous integers of the array
    for index in range(len(array)):
        if array[index] not in array[:index]:
            count = 1
        #iterate through the suceeding elements of the integer to update the count
        for idx in range(index+1,len(array)):
            if array[idx]==array[index]:
                count=count+1
            #add key value pairs to the dictionary
            frequency[array[index]]=count
    # get the key with value==1
    for key in frequency:
        if frequency[key]==1:
            return key
