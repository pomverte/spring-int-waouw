# Spring Integration Waouw

                                           printPayload()                                  ------------> |===exit===|
                                          /              \                                /                    ^
    chevron(i) -> Message -> |===input===|                -> |===output===| -> oddOrEven()                     |
                                          \              /                                \                    |
                                            printHeader()                                  |===odd===| -> addCommitter()
