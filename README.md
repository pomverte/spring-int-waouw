# Spring Integration Waouw

    chevron(0, 1, 2, 3, 4) -> Message -> |===input===| -> banana()                      printPayload()                                  ------------> |===output===|
                                                                   \                   /              \                                /                     ^
                                                                    -> |===fragment===|                -> |===common===| -> oddOrEven()                      |
                                                                   /                   \              /                                \                     |
                                                   twitterAdapter()                      printHeader()                                  |===odd===| -> addUserName()
