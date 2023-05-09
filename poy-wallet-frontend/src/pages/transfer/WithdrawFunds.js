import { LoadingButton } from '@mui/lab';
import { Autocomplete, Button, Card, Grid, Stack, TextField } from '@mui/material';
import { useSnackbar } from 'notistack';
import { useEffect, useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';
import HttpService from '../../services/HttpService';

export default function WithdrawFunds() {
  const defaultValues = {
    amount: '',
    from_wallet_iban: '',
    to_wallet_iban: '',
    description: '',
    transaction_type_id: 1, // set as Transfer by default
  };

  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [formValues, setFormValues] = useState(defaultValues);
  const [to_wallet_ibans, setfrom_wallet_ibans] = useState([]);
  const [to_wallet_iban, setfrom_wallet_iban] = useState();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  useEffect(() => {
    const userId = AuthService.getCurrentUser()?.id;
    HttpService.getWithAuth(`/wallets/users/${userId}`).then((result) => {
      setfrom_wallet_ibans(result.data);
    });
  }, []);

  const handleWalletChange = (event) => {
    setfrom_wallet_iban(event.iban);
    setFormValues({
      ...formValues,
      from_wallet_iban: event.iban,
      to_wallet_iban: event.iban,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    HttpService.postWithAuth('/wallets/withdrawFunds', formValues)
      .then((response) => {
        enqueueSnackbar('Transfer completed successfully', { variant: 'success' });
        console.log(response);
        navigate('/transactions');
      })
      .catch((error) => {
        if (error.response?.data?.errors) {
          error.response?.data?.errors.map((e) => enqueueSnackbar(e.message, { variant: 'error' }));
        } else if (error.response?.data?.message) {
          enqueueSnackbar(error.response?.data?.message, { variant: 'error' });
        } else {
          enqueueSnackbar(error.message, { variant: 'error' });
        }
      });
  };

  return (
    <>
      <Helmet>
        <title> Withdraw Funds | Poy Wallet </title>
      </Helmet>
      <Card>
        <Grid container alignItems="left" justify="left" direction="column" sx={{ width: 400, padding: 5 }}>
          <Stack spacing={3}>
            <TextField
              id="amount"
              name="amount"
              label="Amount"
              autoFocus
              required
              value={formValues.amount}
              onChange={handleInputChange}
            />
            <Autocomplete
              ListboxProps={{ style: { maxHeight: 200, overflow: 'auto' } }}
              required
              disablePortal
              id="to_wallet_iban"
              noOptionsText="no records"
              options={to_wallet_ibans}
              getOptionLabel={(to_wallet_iban) => to_wallet_iban.name}
              isOptionEqualToValue={(option, value) => option.name === value.name}
              onChange={(event, newValue) => {
                handleWalletChange(newValue);
              }}
              renderInput={(params) => <TextField {...params} label="Wallet" />}
            />
            <TextField
              id="description"
              name="description"
              label="Description"
              autoComplete="description"
              required
              value={formValues.description}
              onChange={handleInputChange}
            />
          </Stack>
          <Stack spacing={2} direction="row" alignItems="right" justifyContent="end" sx={{ mt: 4 }}>
            <Button sx={{ width: 120 }} variant="outlined" onClick={() => navigate('/wallets')}>
              Cancel
            </Button>
            <LoadingButton sx={{ width: 120 }} size="large" type="submit" variant="contained" onClick={handleSubmit}>
              Save
            </LoadingButton>
          </Stack>
        </Grid>
      </Card>
    </>
  );
}
