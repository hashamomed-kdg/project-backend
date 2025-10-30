import {Avatar, Divider, Paper, Stack, Typography} from "@mui/material"
import type {Donation} from "../model/donation.ts"
import dayjs from "dayjs"
import relativeTime from "dayjs/plugin/relativeTime"
import {AmountDisplay} from "./AmountDisplay.tsx"

// Extend dayjs with the relativeTime plugin
dayjs.extend(relativeTime)

interface DonationCardProps {
    donation: Donation;
}

export function DonationCard({donation}: DonationCardProps) {
    const relativeTimeAgo = dayjs(donation.timestamp).fromNow()

    return (
        <Paper elevation={1} sx={{p: 2, width: '35rem'}}>
            <Stack direction="row" spacing={4} alignItems="center"
                   divider={<Divider orientation="vertical" flexItem/>}>
                <AmountDisplay amount={donation.amount} currency="€"/>

                <Stack sx={{width: '100%'}}>
                    <Stack direction="row" spacing={1} sx={{justifyContent: 'space-between'}}>
                        <Stack direction="column" alignItems="start">
                            <Typography variant="body2" sx={{mb: -0.7, color: 'text.secondary'}}>
                                {relativeTimeAgo}
                            </Typography>
                            <Typography variant="h5" sx={{color: 'text.primary'}}>
                                {donation.shortMessage}
                            </Typography>
                        </Stack>
                        <Avatar
                            alt="Donator"
                            src={`/img/users/${donation.sponsor.id}.png`}
                            sx={{width: '2.8rem', height: '2.8rem'}}
                        />
                    </Stack>
                    <Stack>
                        <Typography variant="body1" sx={{color: 'text.secondary', textAlign: 'left'}}>
                            {donation.longMessage}
                        </Typography>
                    </Stack>
                </Stack>
            </Stack>
        </Paper>
    )
}
