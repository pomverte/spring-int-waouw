# Spring Integration Waouw

                                                                                    printPayload()                                  ------------> |===exit===|
                                                                                   /              \                                /                    ^
    chevron(0, 1, 2, 3, 4) -> Message -> |===mixed===| -> banana() -> |===input===|                -> |===output===| -> oddOrEven()                     |
                                                                                   \              /                                \                    |
                                                                                     printHeader()                                  |===odd===| -> addCommitter()
