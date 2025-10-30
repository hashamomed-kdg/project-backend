import {Button, Stack, Typography} from "@mui/material"
import {DonationCard} from "./DonationCard.tsx"
import type {PiggyBank} from "../model/piggyBank.ts"
import {DonationDialog} from "./DonationDialog.tsx"
import {useState} from "react"

interface DonationsListProps {
    piggyBank: PiggyBank,
}

export function DonationsList({piggyBank}: DonationsListProps) {
    const [isDialogOpen, setIsDialogOpen] = useState(false)
    const donations = piggyBank.donations
        .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())

    return (
        <>
            <DonationDialog
                piggyBank={piggyBank}
                isOpen={isDialogOpen}
                onClose={() => setIsDialogOpen(false)}
            />
            <Stack>
                <Stack direction="row" sx={{justifyContent: 'space-between'}}>
                    <Typography variant="h5" sx={{fontWeight: 700}}>
                        Your donations to {piggyBank.owner.name}
                    </Typography>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={() => setIsDialogOpen(true)}
                    >
                        Donate
                    </Button>
                </Stack>

                <Stack spacing={2} sx={{mt: 1}}>
                    {donations.length === 0 ? (
                        <Typography
                            variant="body1"
                            color="text.secondary"
                            sx={{textAlign: "center", mt: 2}}
                        >
                            You haven’t made any donations yet. Be the first to support {piggyBank.owner.name}!
                        </Typography>
                    ) : (
                        donations
                            .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())
                            .map((donation) => (
                                <DonationCard donation={donation} key={donation.id}/>
                            ))
                    )}
                </Stack>
            </Stack>
        </>
    )
}
