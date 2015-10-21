# Spring Integration Waouw

                                           printPayload()                                  |===odd===|
                                          /              \                                /
    chevron(i) -> Message -> |===input===|                -> |===common===| -> oddOrEven()
                                          \              /                                \
                                            printHeader()                                  |===output===|
