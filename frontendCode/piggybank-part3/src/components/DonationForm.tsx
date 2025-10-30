import {Box, Button, Paper, Stack, TextField} from "@mui/material"
import {useForm} from "react-hook-form"
import type {DonationFormValues} from "../model/donation.ts"


interface DonationFormProps {
    onSubmit: (newDonation: DonationFormValues) => void;
}

export function DonationForm({onSubmit}: DonationFormProps) {
    const {
        register,
        handleSubmit,
        formState: {errors, isSubmitting},
    } = useForm<DonationFormValues>({
        defaultValues: {
            amount: 10,
            shortMessage: "This is for you ❤️",
            longMessage: "",
        },
    })

    return (
        <Paper variant="outlined" sx={{p: 3, width: "35rem"}}>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack
                    direction="row"
                    spacing={4}
                    divider={
                        <Box
                            component="hr"
                            sx={(theme) => ({
                                border: `1px solid ${theme.palette.divider}`,
                            })}
                        />
                    }
                >
                    <TextField
                        label="Donation Amount (€)"
                        variant="outlined"
                        type="number"
                        required
                        error={!!errors.amount}
                        helperText={errors.amount?.message}
                        {...register("amount", {
                            required: "Amount is required",
                            valueAsNumber: true,
                            validate: (v) =>
                                Number.isFinite(v) && v > 0 || "Enter a positive number",
                        })}
                    />

                    <Stack>
                        <TextField
                            label="Short Message"
                            variant="outlined"
                            required
                            fullWidth
                            sx={{mb: 1}}
                            error={!!errors.shortMessage}
                            helperText={errors.shortMessage?.message}
                            {...register("shortMessage", {
                                required: "Short message is required",
                                maxLength: {value: 140, message: "Max 140 characters"},
                            })}
                        />

                        <TextField
                            label="Long Message"
                            variant="outlined"
                            fullWidth
                            multiline
                            rows={3}
                            sx={{mb: 2}}
                            error={!!errors.longMessage}
                            helperText={errors.longMessage?.message}
                            {...register("longMessage", {
                                maxLength: {value: 1000, message: "Max 1000 characters"},
                            })}
                        />
                    </Stack>
                </Stack>

                <Button
                    variant="contained"
                    color="primary"
                    type="submit"
                    disabled={isSubmitting}
                    sx={{mt: 2}}
                >
                    Donate
                </Button>
            </form>
        </Paper>
    )
}
