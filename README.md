# Spring Integration Waouw

                                                                                       printPayload()                                  ------------> |===output===|
                                                                                      /              \                                /                     ^
    chevron(0, 1, 2, 3, 4) -> Message -> |===input===| -> banana() -> |===fragment===|                -> |===common===| -> oddOrEven()                      |
                                                                                      \              /                                \                     |
                                                                                        printHeader()                                  |===odd===| -> addUserName()
