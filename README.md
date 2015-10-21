# Spring Integration Waouw

                                           printPayload()                                  ------------> |===output===|
                                          /              \                                /                     ^
    chevron(i) -> Message -> |===input===|                -> |===common===| -> oddOrEven()                      |
                                          \              /                                \                     |
                                            printHeader()                                  |===odd===| -> addUserName()
